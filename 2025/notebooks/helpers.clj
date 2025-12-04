(ns helpers)

(defn to-int [str]
  (Integer/parseInt str))

(defn to-long [str]
  (Long/parseLong str))

(defn char->int [char]
  (Character/digit char 10))
