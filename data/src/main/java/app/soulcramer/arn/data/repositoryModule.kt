package app.soulcramer.arn.data

import org.koin.core.module.Module
import org.koin.dsl.module

private val dataModule: Module = module {

    factory {
        UserDataRepository(get(), get())
    }
}