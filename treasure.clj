(ns treasure
  (:gen-class)
  (:require [clojure.string :as str]))

(def  st (slurp "map.txt"))

(def full(clojure.string/split-lines st))

(def board (to-array-2d  full))

(def resultboard (to-array-2d  full))

(def visited (to-array-2d  full))

(def tried (to-array-2d  full))

;;Make the visited Matrix o's
(def rowlen (alength visited))
(def rowlenlimit (- rowlen 1 ))
(loop  [row rowlenlimit]

  (when (and (> row -1) )
    (def collen (alength (aget visited row)))
    (def collenlimit (- collen 1 ))
    (loop  [col collenlimit]

      (when (and (> col -1) )
        (aset visited row col 0)
        ;(println row col)
        (recur (- col 1))
        )
      )
    (recur (- row 1))
    )
  )

(do
  (println "This is my challenge:")
  (println)
  (doseq [rows board]
    (doseq [ cell rows]
      (print cell)
      )
    (println)
    )
  (println)
  )


;Make the Tried Matrix o's
(def rowlen (alength visited))
(def rowlenlimit (- rowlen 1 ))
(loop  [row rowlenlimit]
  (when (and (> row -1) )
    (def collen (alength (aget visited row)))
    (def collenlimit (- collen 1 ))
    (loop  [col collenlimit]
      (when (and (> col -1) )
        (aset tried row col 0)
        (recur (- col 1))
        )
      )
    (recur (- row 1))
    )
  )
(def First (aget board 0 0))

(def FirstValue (str First ""))
(if (= FirstValue "-")
  (do
    (aset visited 0 0 1)
    (aset resultboard  0 0 "+")
    )
  )
(if (= FirstValue "#")
  (do
    (aset visited 0 0 0)
    (aset resultboard  0 0 "#")
    )
  )

(def row 0)
(def col 0)





(def pathrow (to-array [1,0,0,-1]))

(def pathcol (to-array [0,1,-1,0]))


(defn isValid [[board visited  tried row col]]
  (def rowlen (alength visited))

  (if (and (>= row 0)(< row rowlen))
    (do
      (def collen (alength (aget visited row))))
    (do
      (def collen 0)
      )
    )
  (if (and (>= row 0) (< row rowlen) (>= col 0)  (< col collen) )

    (do
      (def svisited (aget visited row col))
      (def sesvisited (str svisited ""))

      (if (= sesvisited "0")
        (do
          (def s (aget board row col))
          (def se (str s ""))
          (if (or(= se "@") (= se "-"))
            (true? true)
            (true? false)
            )
          )
        )
      )
    false
    )

  )

(defn findthepath[[board visited  tried row col ]]


  (def s (aget board row col))

  (def se (str s ""))
  (if (= se "@")

    (do
      (def decision 1)
      (aset resultboard  row col "@")
      (println "Woo hoo, I found the treasure :-)")
      (println)
      (doseq [rows resultboard]
        (doseq [ cell rows]
          (print cell)
          )
        (println)
        )
      (System/exit 0)
      )
    (do
      (doseq [n [0 1 2 3]]
        (def respathnow (aget pathrow n))
        (def rescolnow (aget pathcol n))
        (def respathrownowval (+ row respathnow))
        (def respathcolnowval (+ col rescolnow))

        (if (isValid[board visited  tried respathrownowval respathcolnowval])
          (do
            (aset visited respathrownowval respathcolnowval 1)
            (aset resultboard  respathrownowval respathcolnowval "+")
            (aset tried respathrownowval respathcolnowval 1)
            (def tailrecur 0)
            (findthepath [board visited  tried respathrownowval respathcolnowval ])

            (def rowlen (alength visited))

            (if (and (>= row 0)(< row rowlen))
              (do
                (def pathonenegative (- rowlen 1 ))
                (def pathtwonegative (- rowlen 2 ))
                )
              )
            ( if (< respathrownowval pathonenegative)
              (do
                (def oldrow (+ respathrownowval 1))
                (def oldcol respathcolnowval)
                (aset visited oldrow oldcol 0)
                (aset resultboard  oldrow oldcol "!")
                )
              )

            ( if (and (= n 3) ( = 1 tailrecur)(< oldrow pathonenegative))
              (do
                (def oldrow (+ oldrow 1))
                (aset visited oldrow oldcol 0)
                (aset resultboard  oldrow oldcol "!")
                )
              )
            ( if (and (= n 3)(< oldrow pathtwonegative))
              (do
                (def oldrow (+ oldrow 1))
                (def oldcol oldcol)
                (aset visited oldrow oldcol 0)
                (aset resultboard  oldrow oldcol "!")
                (def tailrecur 1)
                )
              )
            )
          )
        )
      )
    )
  )
(findthepath [board visited  tried 0 0 ])

(if (not= 1 decision)

  (do
    (println "Uh oh, I could not find the treasure :-(")
    (println)
    (aset resultboard  0 0 "!")
    (doseq [rows resultboard]
      (doseq [ cell rows]
        (if (= cell "+")
          (print "!")
          (print cell)
                    )
        )
      (println)
      )

    )
  )




