package com.freeamazoncards.amazongifts.cardsforamazon.inject

import com.freeamazoncards.amazongifts.cardsforamazon.core.MyApplication
import com.freeamazoncards.amazongifts.cardsforamazon.core.services.ClaimService
import com.freeamazoncards.amazongifts.cardsforamazon.screens.dialogs.LoginDialog
import com.freeamazoncards.amazongifts.cardsforamazon.screens.dialogs.PromocodeDialog
import com.freeamazoncards.amazongifts.cardsforamazon.screens.dialogs.RedeemDialog
import com.freeamazoncards.amazongifts.cardsforamazon.screens.dialogs.SignupDialog
import com.freeamazoncards.amazongifts.cardsforamazon.screens.BaseActivity
import dagger.Component

@Component(modules = arrayOf(AppModule::class, MainModule::class))
interface AppComponent {

    fun inject(screen: BaseActivity)
    fun inject(app: MyApplication)
    fun inject(dialog: LoginDialog)
    fun inject(dialog: SignupDialog)
    fun inject(dialog: PromocodeDialog)
    fun inject(dialog: RedeemDialog)
    fun inject(service: ClaimService)
}
