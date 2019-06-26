package app.soulcramer.arn.remote.model.user.graphql

import com.squareup.moshi.JsonClass

/**
 * Representation for a [UserListResponse] fetched from the API
 */
@JsonClass(generateAdapter = true)
class UserListResponse(val data: UserList)
class UserList(val allUser: List<GraphQlUserModel>)