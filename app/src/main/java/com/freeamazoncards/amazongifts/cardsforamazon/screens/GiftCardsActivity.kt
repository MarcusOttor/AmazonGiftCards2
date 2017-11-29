package com.freeamazoncards.amazongifts.cardsforamazon.screens

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import com.freeamazoncards.amazongifts.cardsforamazon.R
import kotlinx.android.synthetic.main.giftcards_activity.*
import kotlinx.android.synthetic.main.scroll_item.view.*
import kotlinx.android.synthetic.main.toolbar.*

class GiftCardsActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.giftcards_activity)

        bindCoinView()
        bind()

        initCardsLIst()

        toolbarText.text = "Gift Cards"

        initBanner()
    }

    @OnClick(R.id.addCoinsText)
    fun back(view: View) {
        startActivity(Intent(this, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }

    private fun initCardsLIst() {
        giftCardsRecycler.setHasFixedSize(true)
        giftCardsRecycler.layoutManager = GridLayoutManager(this, 2,
                GridLayoutManager.VERTICAL, false)
        giftCardsRecycler.adapter = CardsScrollAdapter(arrayOf(
                R.drawable.card_25, R.drawable.card_50,
                R.drawable.card_75,  R.drawable.card_100,
                R.drawable.card_150, R.drawable.card_200))
        giftCardsRecycler.adapter.notifyDataSetChanged()
    }

    inner class CardsScrollAdapter(private val cards: Array<Int>): RecyclerView.Adapter<CardsScrollAdapter.CardsScrollHolder>() {
        override fun getItemCount(): Int {
            return cards.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardsScrollHolder {
            return CardsScrollHolder(LayoutInflater.from(parent?.context).inflate(R.layout.scroll_item, parent, false))
        }

        override fun onBindViewHolder(holder: CardsScrollHolder?, position: Int) {
            holder?.itemView!!.cardImage.setImageDrawable(resources.getDrawable(cards[position]))
            holder.itemView!!.cardImage.setOnClickListener {
                dialogsManager.showRedeemDialog(supportFragmentManager, cards[position],
                        when (cards[position]) {
                            R.drawable.card_25 -> 12000
                            R.drawable.card_50 -> 20000
                            R.drawable.card_75 -> 28000
                            R.drawable.card_100 -> 36000
                            R.drawable.card_150 -> 48000
                            R.drawable.card_200 -> 60000
                            else -> 12000
                        })
            }
        }

        inner class CardsScrollHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }
}
