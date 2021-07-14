package com.tacheyourself.myassistant.model;



public class Hotel {

 private String mNom;
 private String mAdresse;
 private String mPointFort;
 private String mLieuxApprox;
 private String mAearportProche;
 private float mEvaluation;

 public Hotel() {
 }

 public Hotel(String nom, String adresse, String pointFort, String lieuxApprox, String aearportProche, float evaluation) {
  mNom = nom;
  mAdresse = adresse;
  mPointFort = pointFort;
  mLieuxApprox = lieuxApprox;
  mAearportProche = aearportProche;
  mEvaluation = evaluation;
 }

 public String getNom() {
  return mNom;
 }

 public void setNom(String nom) {
  mNom = nom;
 }

 public String getAdresse() {
  return mAdresse;
 }

 public void setAdresse(String adresse) {
  mAdresse = adresse;
 }

 public String getPointFort() {
  return mPointFort;
 }

 public void setPointFort(String pointFort) {
  mPointFort = pointFort;
 }

 public String getLieuxApprox() {
  return mLieuxApprox;
 }

 public void setLieuxApprox(String lieuxApprox) {
  mLieuxApprox = lieuxApprox;
 }

 public String getAearportProche() {
  return mAearportProche;
 }

 public void setAearportProche(String aearportProche) {
  mAearportProche = aearportProche;
 }

 public float getEvaluation() {
  return mEvaluation;
 }

 public void setEvaluation(float evaluation) {
  mEvaluation = evaluation;
 }


 @Override
 public String toString() {
  return
          "Nom=" + mNom + '\n' +
          "Adresse=" + mAdresse + '\n';

 }
}
