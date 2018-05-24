package studio.lunabee.arn.vo.animelist

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

internal class AnimeListItemDeserializer : JsonDeserializer<AnimeListItem> {
    @Throws(JsonParseException::class)
    override fun deserialize(je: JsonElement, type: Type,
        jdc: JsonDeserializationContext): AnimeListItem {
        // Get the "content" element from the parsed JSON
        val content = je.asJsonObject.get("items")
        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return Gson().fromJson(content, AnimeListItem::class.java)
    }
}