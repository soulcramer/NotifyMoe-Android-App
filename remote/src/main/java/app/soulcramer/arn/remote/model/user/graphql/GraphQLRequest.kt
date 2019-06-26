package app.soulcramer.arn.remote.model.user.graphql

import com.squareup.moshi.JsonClass

/**
 * Representation for a [GraphQLRequest] fetched from the API
 */
@JsonClass(generateAdapter = true)
class GraphQLRequest(val query: String)