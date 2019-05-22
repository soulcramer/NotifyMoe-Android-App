package app.soulcramer.arn.remote

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

class AnimeListTypeAdapterFactory : TypeAdapterFactory {

    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T> {

        val delegate = gson.getDelegateAdapter(this, type)
        val elementAdapter = gson.getAdapter(JsonElement::class.java)

        return object : TypeAdapter<T>() {

            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: T) {
                delegate.write(out, value)
            }

            @Throws(IOException::class)
            override fun read(jsonReader: JsonReader): T {

                var jsonElement = elementAdapter.read(jsonReader)
                if (jsonElement.isJsonObject) {
                    val jsonObject = jsonElement.asJsonObject
                    if (jsonObject.has("items") && jsonObject.get("items").isJsonArray) {
                        jsonElement = jsonObject.get("items")
                        val items = jsonElement.asJsonArray.map { item ->
                            item.asJsonObject.addProperty("userId", jsonObject.get("userId").asString)
                            return@map item
                        }
                        jsonElement = JsonArray(items.size).apply {
                            items.forEach {
                                add(it)
                            }
                        }
                    }
                }

                return delegate.fromJsonTree(jsonElement)
            }
        }.nullSafe()
    }
}
