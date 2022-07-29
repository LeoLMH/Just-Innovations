package cn.edu.sjtu.yyqdashen.presentation

import java.util.*
internal object ExpandableListData {
    val data: HashMap<String, List<String>>
        get() {
            val expandableListDetail =
                HashMap<String, List<String>>()
            val myFavCricketPlayers: MutableList<String> =
                ArrayList()
            myFavCricketPlayers.add("MS.Dhoni")

            val myFavFootballPlayers: MutableList<String> = ArrayList()
            myFavFootballPlayers.add("Cristiano Ronaldo ahdf")

            val myFavTennisPlayers: MutableList<String> = ArrayList()
            myFavTennisPlayers.add("Roger Federer ah dflash dfklhasd lkf halkdshf laewiof ghoiaewhf2 oy 389g2k3rg3kj2g rjkuhg4kjg324 kj23g 4kj2g3 4jkg 2k34g k2")

            expandableListDetail["Pace"] = myFavCricketPlayers
            expandableListDetail["Volume"] = myFavFootballPlayers
            expandableListDetail["Tone"] = myFavTennisPlayers
            return expandableListDetail
        }

}