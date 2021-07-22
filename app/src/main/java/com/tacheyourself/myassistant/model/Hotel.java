package com.tacheyourself.myassistant.model;



public class Hotel {

 private String mNom;
 private String mAdresse;
 private String mPointFort;
 private String mLieuxApprox;
 private float mEvaluation;
 private int mPrice;
 private int mStars;

 public Hotel() {
 }

 public Hotel(String nom, String adresse, String pointFort, String lieuxApprox, float evaluation,int price,int stars) {
  mNom = nom;
  mAdresse = adresse;
  mPointFort = pointFort;
  mLieuxApprox = lieuxApprox;
  mEvaluation = evaluation;
  mPrice=price;
  mStars=stars;
 }

 public int getPrice() {
  return  mPrice;
 }

 public void setPrice(int price) {
  mPrice = price;
 }

 public int getStars() {
  return mStars;
 }

 public void setStars(int stars) {
  mStars = stars;
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
