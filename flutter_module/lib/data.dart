import 'dart:convert';

import "package:json_annotation/json_annotation.dart";

@JsonSerializable()
class SpeciceBack {
  var offset = 0;
  var limit = 100;
  var endOfRecords = false;
  List<Specice> results = [];

  SpeciceBack.fromJson(Map<String, dynamic> json)
      : offset = json['offset'],
        limit = json['limit'],
        endOfRecords = json['endOfRecords'],
        results = (json['results'] as List).map((s) {
          var sp = new Specice.fromJson(s);

          return sp;
        }).toList();

  Map<String, dynamic> toJson() => {
        'offset': offset,
        'limit': limit,
        'endOfRecords': endOfRecords,
        'result': results,
      };
}

class Specice {
  var key = 0;
  var nubKey = 0;
  var nameKey = 0;
  var taxonID = "";
  var sourceTaxonKey = 0;
  var kingdom = "";
  var kingdomKey = 0;
  var datasetKey = "";
  var constituentKey = "";
  var scientificName = "";
  var canonicalName = "";
  var authorship = "";
  var nameType = "";
  var rank = "";
  var origin = "";
  var taxonomicStatus = "";
  List nomenclaturalStatus = [];

  var remarks = "";
  var numDescendants = 0;
  var lastCrawled = "";
  var lastInterpreted = "";
  List issues = [];

  var synonym = false;
  var phylum = "";
  var phylumKey = 0;
  var parentKey = 0;
  var parent = "";
  var acceptedKey = 0;
  var accepted = "";

  Specice.fromJson(Map<String, dynamic> json)
      : key = json['key'],
        nubKey = json['nubKey'],
        nameKey = json['nameKey'],
        taxonID = json['taxonID'],
        sourceTaxonKey = json['sourceTaxonKey'],
        kingdom = json['kingdom'],
        kingdomKey = json['kingdomKey'],
        datasetKey = json['datasetKey'],
        constituentKey = json['constituentKey'],
        scientificName = json['scientificName'],
        canonicalName = json['canonicalName'],
        authorship = json['authorship'],
        nameType = json['nameType'],
        rank = json['rank'],
        origin = json['origin'],
        taxonomicStatus = json['taxonomicStatus'],
        nomenclaturalStatus = json['nomenclaturalStatus'] as List,
        remarks = json['remarks'],
        numDescendants = json['numDescendants'],
        lastCrawled = json['lastCrawled'],
        lastInterpreted = json['lastInterpreted'],
        issues = json['issues'] as List,
        synonym = json['synonym'],
        phylum = json['phylum'],
        phylumKey = json['phylumKey'],
        parentKey = json['parentKey'],
        parent = json['parent'],
        acceptedKey = json['acceptedKey'],
        accepted = json['accepted'];
}
