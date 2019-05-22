package app.soulcramer.arn.data

import app.soulcramer.arn.database.databaseModule
import app.soulcramer.arn.service.serviceModule
import org.koin.core.module.Module
import org.koin.dsl.module

private val repositoryModule: Module = module {

    factory {
        AnimeListRepository(get(), get(), get())
    }

    factory {
        UserRepository(get(), get())
    }

    factory {
        AnimeRepository(get(), get())
    }
}
val repositoryModules = listOf(repositoryModule, databaseModule, serviceModule)
