(ns us.sellars.adventofcode
  (:require [clojure.java.io :as io])
  (:gen-class))

(defn part-1
  "Reads input frequencies and sums them."
  []
  (->> "input.txt"
       io/as-file
       io/reader
       line-seq
       (map #(Long. %))
       (reduce + 0)
       println))
(part-1)

(defn first-frequency-seen-twice
  [freqs]
  (loop [[f & fs] freqs
         observed #{}]
    ;(println f (count observed))
    (if (contains? observed f)
      f
      (recur fs (conj observed f))))) 

(defn part-2
  "Reads input frequencies and sums them."
  []
  (->> "input.txt"
       io/as-file
       io/reader
       line-seq
       (map #(Long. %))
       cycle
       (reductions + 0)
       first-frequency-seen-twice
       println))

(part-2)
