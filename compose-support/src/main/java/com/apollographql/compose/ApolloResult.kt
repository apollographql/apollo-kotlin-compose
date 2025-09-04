package com.apollographql.compose

import com.apollographql.apollo.api.Executable

sealed interface ApolloResult<D: Executable.Data> {
  class Success<D: Executable.Data>(
    /**
     * Parsed response
     */
    val data: D,
    /**
     * Extensions of GraphQL protocol, arbitrary map of key [String] / value [apollo.compose.JsonElement] sent by server along with the response.
     *
     * See https://spec.graphql.org/October2021/#sel-EAPHJCAACCAruC
     */
    val extensions: Map<String, Any?>,
  ): ApolloResult<D>

  /**
   * An error happened
   */
  class Error<D: Executable.Data>(val exception: Exception): ApolloResult<D>
}
