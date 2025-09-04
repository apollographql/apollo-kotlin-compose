package com.apollographql.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.apollographql.apollo.ApolloClient

@Composable
fun ApolloClientProvider(
  client: () -> ApolloClient,
  content: @Composable () -> Unit,
) {
  CompositionLocalProvider(
    LocalApolloClient provides client(),
  ) {
    content()
  }
}


internal val LocalApolloClient = compositionLocalOf<ApolloClient> {
  error("ApolloClientProvider must be part of the call hierarchy")
}
