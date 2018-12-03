(ns us.sellars.adventofcode-2
  (:require [clojure.java.io :as io]
            [clojure.string :as string])
  (:gen-class))

(defn- rudimentary-checksum
  [m]
  (apply * (map m [2 3])))

(defn part-1
  "Reads input and forms a rudimentary checksum."
  []
  (->> "us.sellars.adventofcode/input-2.txt"
       io/as-file
       io/reader
       line-seq
       (map frequencies)
       (map #(zipmap (vals %) (repeat 1)))
       (reduce (partial merge-with +) {})
       rudimentary-checksum))

(part-1)

(defn find-same-letters-of-box
  [ids]
  (->> (for [a ids
             b ids
             :when (= (dec (count a))
                      (count (filter identity (map = a b))))]
        (filter identity (map #(when (= %1 %2) %2) a b)))
       first
       string/join))
       
(defn part-2
  "Finds near-matches."
  []
  (->> "us.sellars.adventofcode/input-2.txt"
       io/as-file
       io/reader
       line-seq
       find-same-letters-of-box))

(part-2)
