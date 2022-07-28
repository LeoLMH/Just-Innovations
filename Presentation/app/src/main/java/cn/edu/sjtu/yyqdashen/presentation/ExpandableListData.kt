package cn.edu.sjtu.yyqdashen.presentation

import java.util.*
internal object ExpandableListData {
    val data: HashMap<String, List<String>>
        get() {
            val expandableListDetail =
                HashMap<String, List<String>>()
            val myFavCricketPlayers: MutableList<String> =
                ArrayList()
            myFavCricketPlayers.add("MS.Dhoni asdf lka dlkah lkewh flkh ealkf hleskhf lkas hflkash eklfah flkh aselkfh aklshf lkashefkla hsdlkfh alsd khflasdf ")

            val myFavFootballPlayers: MutableList<String> = ArrayList()
            myFavFootballPlayers.add("Cristiano Ronaldo ahdf adhf lakdsh flkaw eklfh lskeh flka hflksah flkashd flkash dfkl hasldkfh lkasdh flsad dfkhla")

            val myFavTennisPlayers: MutableList<String> = ArrayList()
            myFavTennisPlayers.add("Roger Federer ah dflash dfklhasd lkf halkdshf laewiof ghoiaewhf2 oy 389g2k3rg3kj2g rjkuhg4kjg324 kj23g 4kj2g3 4jkg 2k34g k2")

            expandableListDetail["Pace"] = myFavCricketPlayers
            expandableListDetail["Volume"] = myFavFootballPlayers
            expandableListDetail["Tone"] = myFavTennisPlayers
            return expandableListDetail
        }

}