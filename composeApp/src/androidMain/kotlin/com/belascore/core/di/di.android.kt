package com.belascore.core.di

import com.belascore.data.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { getDatabaseBuilder(get()) }
}
