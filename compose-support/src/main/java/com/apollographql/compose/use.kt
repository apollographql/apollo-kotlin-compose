@file:Suppress("UNCHECKED_CAST")

package com.apollographql.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.apollographql.apollo.annotations.ApolloExperimental
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Subscription
import com.apollographql.apollo.exception.DefaultApolloException
import kotlinx.coroutines.flow.map


@ApolloExperimental
@Composable
fun <D: Query.Data> useQuery(query: Query<D>): State<ApolloResult<D>?> {
  return useOperation(query)
}

@ApolloExperimental
@Composable
fun <D: Mutation.Data> useMutation(mutation: Mutation<D>): State<ApolloResult<D>?> {
  return useOperation(mutation)
}

@ApolloExperimental
@Composable
fun <D: Subscription.Data> useSubscription(subscription: Subscription<D>): State<ApolloResult<D>?> {
  return useOperation(subscription)
}

@ApolloExperimental
@Composable
fun <D: Operation.Data> useOperation(operation: Operation<D>): State<ApolloResult<D>?> {
  val client = LocalApolloClient.current

  val flow = remember {
    val call = when (operation) {
      is Query<*> -> client.query(operation)
      is Mutation<*> -> client.mutation(operation)
      is Subscription<*> -> client.subscription(operation)
      else -> error("")
    }
    call.toFlow().map {
      when  {
        it.exception != null -> {
          ApolloResult.Error<D>(it.exception!!)
        }
        it.data != null -> {
          ApolloResult.Success<D>(it.data!! as D, it.extensions)
        }
        else -> {
          ApolloResult.Error<D>(DefaultApolloException("An error happened"))
        }

      }
    }
  }


  return flow.collectAsState(initial = null)
}

