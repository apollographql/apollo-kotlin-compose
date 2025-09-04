@file:Suppress("UNCHECKED_CAST")

package com.apollographql.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.apollographql.apollo.annotations.ApolloExperimental
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Subscription


@ApolloExperimental
@Composable
fun <D: Query.Data> useQuery(query: Query<D>): State<ApolloResult<D>> {
  return useOperation(query)
}

@ApolloExperimental
@Composable
fun <D: Mutation.Data> useMutation(mutation: Mutation<D>): State<ApolloResult<D>> {
  return useOperation(mutation)
}

@ApolloExperimental
@Composable
fun <D: Subscription.Data> useSubscription(subscription: Subscription<D>): State<ApolloResult<D>> {
  return useOperation(subscription)
}

@ApolloExperimental
@Composable
fun <D: Operation.Data> useOperation(operation: Operation<D>): State<ApolloResult<D>> {
  val client = LocalApolloClient.current
  val call = when (operation) {
    is Query<*> -> client.query(operation)
    is Mutation<*> -> client.mutation(operation)
    is Subscription<*> -> client.subscription(operation)
    else -> error("")
  }

  return call.toState() as State<ApolloResult<D>>
}

