package com.freeamazoncards.amazongifts.cardsforamazon.core.advertisements

import android.app.Activity
import android.view.View
import com.freeamazoncards.amazongifts.cardsforamazon.R
import com.freeamazoncards.amazongifts.cardsforamazon.core.analytics.Analytics
import com.freeamazoncards.amazongifts.cardsforamazon.core.managers.PreferencesManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class AdmobBanner(private var preferencesManager: PreferencesManager,
                  activity: Activity) {

    private var adView: AdView? = null

    init {
        adView = activity.findViewById(R.id.adView)
        adView?.adListener = object : AdListener() {
            override fun onAdLeftApplication() {
                Analytics.report(Analytics.BANNER, Analytics.ADMOB, Analytics.CLICKED)
            }

            override fun onAdLoaded() {
                adView?.visibility = View.VISIBLE
            }
        }

        var request = AdRequest.Builder().build()
        adView?.loadAd(request)
    }
}
