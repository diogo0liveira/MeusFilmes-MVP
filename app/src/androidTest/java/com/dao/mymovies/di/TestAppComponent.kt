package com.dao.mymovies.di

import com.dao.mymovies.MyMoviesApplication
import com.dao.mymovies.data.repository.FakeRepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import android.app.Application



/**
 * Created in 13/05/19 10:14.
 *
 * @author Diogo Oliveira.
 */
@Singleton
@Component(modules =
[
    AndroidSupportInjectionModule::class,
    TestActivityBindingModule::class,
    AppModule::class
])
interface TestAppComponent : AndroidInjector<MyMoviesApplication>
{
//    @Component.Factory
//    abstract class Builder : AndroidInjector.Factory<MyMoviesApplication>

    @Component.Factory
    interface Builder /*: AndroidInjector.Factory<MyMoviesApplication>*/
    {
//        fun build(): TestAppComponent

//        @BindsInstance
//        fun repositoryModule(module: FakeRepositoryModule): TestAppComponent

        fun repositoryModule(@BindsInstance  module: FakeRepositoryModule): TestAppComponent
    }
}