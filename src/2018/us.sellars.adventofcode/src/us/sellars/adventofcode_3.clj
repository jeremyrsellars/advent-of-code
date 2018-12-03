(ns us.sellars.adventofcode-3
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pprint]
            [clojure.set :as set]
            [clojure.string :as string])
  (:gen-class))

(defn part-1
  []
  (->> "input-3.txt"
       io/as-file
       io/reader
       line-seq
       (mapcat (fn [s]
                  (let [[c x y w h](map #(Long. %) (rest (re-find #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" s)))]
                    (for [X (range 0 w)
                          Y (range 0 h)]
                      [(+ x X) (+ y Y)]))))
       frequencies
       ; sort
       (filter (fn [[x v]](> v 1)))
       count))
       ; pprint/pprint))

(part-1)


(defn part-2
  [input-filename]
  (let [coords
          (->> input-filename
               io/as-file
               io/reader
               line-seq
               (mapcat (fn [s]
                          (let [[c x y w h](map #(Long. %) (rest (re-find #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" s)))]
                            (for [X (range 0 w)
                                  Y (range 0 h)]
                              [(+ x X) (+ y Y) c])))))
          coords-by-claim (->> coords
                               (group-by #(nth % 2))
                               (reduce-kv (fn [m claim v](assoc m claim (into (sorted-set) (map pop v)))) {}))
          overlapping-claims
            (->>  (for [x coords-by-claim
                        y coords-by-claim
                        :when (not (identical? x y))]
                    [x y])
                  (pmap
                   (fn [[[c1 s1] [c2 s2]]]
                     (when (and (not (= c1 c2))
                                (seq (set/intersection s1 s2)))
                      [c1 c2]))) 
                  (reduce into #{}))]   
    (println :coords)
    (pprint/pprint (take 40 (sort coords)))
    ; (println :claims-by-coord)
    ; (pprint/pprint (take 40 claims-by-coord))
    (println :coords-by-claim)
    (pprint/pprint (take 40 coords-by-claim))
    (println :overlapping-claims)
    (pprint/pprint (take 40 overlapping-claims))   
    (->> coords-by-claim
         keys
         (remove overlapping-claims))))
         

(part-2 "input-3.sample.txt")
(part-2 "input-3.txt")
