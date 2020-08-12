package ls.yylx.lscodestore.basemodule.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


data class SpeciesBack(
        var offset: Int = 0,
        var limit: Int = 0,
        var endOfRecords: Boolean = false,
        var results: List<Specie> = listOf()
)


@Entity(indices = arrayOf(Index(value = ["key"], unique = true)))
data class Specie(
        @PrimaryKey(autoGenerate = true) var id: Long  = 0L,
        var key: Int = 0,
        var nubKey: Int = 0,
        var nameKey: Int = 0,
        var taxonID: String = "",
        var sourceTaxonKey: Int = 0,
        var kingdom: String = "",
        var kingdomKey: Int = 0,
        var datasetKey: String = "",
        var constituentKey: String = "",
        var scientificName: String = "",
        var canonicalName: String = "",
        var authorship: String = "",
        var nameType: String = "",
        var rank: String = "",
        var origin: String = "",
        var taxonomicStatus: String = "",
//        var nomenclaturalStatus: List<String> = listOf(),
        var remarks: String = "",
        var numDescendants: Int = 0,
        var lastCrawled: String = "",
        var lastInterpreted: String = "",
//        var issues: List<String> = listOf(),
        var synonym: Boolean = false,
        var phylum: String = "",
        var phylumKey: Int = 0,
        var parentKey: Int = 0,
        var parent: String = "",
        var acceptedKey: Int = 0,
        var accepted: String = ""
)