package com.freeamazoncards.amazongifts.cardsforamazon.screens.dialogs

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import butterknife.ButterKnife
import butterknife.OnClick
import com.freeamazoncards.amazongifts.cardsforamazon.AppTools
import com.freeamazoncards.amazongifts.cardsforamazon.R
import com.freeamazoncards.amazongifts.cardsforamazon.core.MyApplication
import com.freeamazoncards.amazongifts.cardsforamazon.core.analytics.Analytics
import com.freeamazoncards.amazongifts.cardsforamazon.core.managers.CoinsManager
import com.freeamazoncards.amazongifts.cardsforamazon.core.managers.DialogsManager
import com.freeamazoncards.amazongifts.cardsforamazon.core.managers.PreferencesManager
import com.freeamazoncards.amazongifts.cardsforamazon.inject.AppModule
import com.freeamazoncards.amazongifts.cardsforamazon.inject.DaggerAppComponent
import com.freeamazoncards.amazongifts.cardsforamazon.screens.GiftCardsActivity
import kotlinx.android.synthetic.main.dialog_redeem.view.*
import javax.inject.Inject
import kotlin.concurrent.thread

@SuppressLint("ValidFragment")
class RedeemDialog(private val card: Int, private var price: Int) : DialogFragment() {

    @Inject lateinit var preferencesManager: PreferencesManager
    @Inject lateinit var coinsManager: CoinsManager
    @Inject lateinit var dialogsManager: DialogsManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        DaggerAppComponent.builder()
                .appModule(AppModule(context))
                .mainModule((activity.application as MyApplication).mainModule)
                .build().inject(this)

        var view = inflater?.inflate(R.layout.dialog_redeem, container, false)
        view?.redeemCardImage?.setImageDrawable(resources.getDrawable(card))

        ButterKnife.bind(this, view!!)

        return view
    }

    @OnClick(R.id.redeemBtn)
    fun redeem() {
        if (AppTools.isNetworkAvaliable(activity)) {
            if (coinsManager.getCoins() >= price) {
                if (AppTools.isEmailAdressCorrect(view?.rootView?.emailText?.text.toString())) {
                    var dismisser = dialogsManager.showProgressDialog(activity.supportFragmentManager)
                    thread {
                        Thread.sleep(3000)
                        Analytics.report(Analytics.WITHDRAW, Analytics.AMOUNT, price.toString())
                        activity.runOnUiThread {
                            dismisser.dismiss()
                            coinsManager.subtractCoins(price)
                            (activity as GiftCardsActivity).updateCoins()
                            dialogsManager.showAlertDialog(activity.supportFragmentManager,
                                    "You will receive your gift card in 3 - 7 days!", {
                                dismiss()
                            })
                        }
                    }
                } else {
                    dialogsManager.showAlertDialog(activity.supportFragmentManager,
                            "Email is not valid!", {
                        (activity as GiftCardsActivity).admobInterstitial?.show {  }
                    })
                }
            } else {
                dialogsManager.showAlertDialog(activity.supportFragmentManager,
                        "Not enough coins! You need $price coins", {
                    (activity as GiftCardsActivity).admobInterstitial?.show {  }
                })
            }
        } else {
            dialogsManager.showAlertDialog(activity.supportFragmentManager,
                    "No internet connection!", {
                (activity as GiftCardsActivity).admobInterstitial?.show {  }
            })
        }

    }
}
