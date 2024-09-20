import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import kotlinx.serialization.json.Json
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
private const val BASE_URL = "https://api.disneyapi.dev/"

interface DisneyApiService {

    // Obtener todos los personajes con paginación
    @GET("character")
    suspend fun getAllCharacters(@Query("page") page: Int = 1): DisneyCharacterResponseList
}




object DisneyApi {


    val retrofitService: DisneyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DisneyApiService::class.java)
    }
}

@Serializable
data class DisneyCharacterResponseList(
    val info: Info,
    val data: List<DisneyCharacter> // Aquí especificamos que la data es una lista de DisneyCharacter
)
@Serializable
data class Info(
    val count: Int
)

@Serializable
data class DisneyCharacter(
    val _id: Int,
    val films: List<String>,
    val shortFilms: List<String>,
    val tvShows: List<String>,
    val videoGames: List<String>,
    val parkAttractions: List<String>,
    val allies: List<String>,
    val enemies: List<String>,
    val name: String,
    val imageUrl: String,
    val url: String
)

