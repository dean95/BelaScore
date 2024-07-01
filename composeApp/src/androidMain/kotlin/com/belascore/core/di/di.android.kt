package com.belascore.core.di

import com.belascore.data.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::getDatabaseBuilder)
}
