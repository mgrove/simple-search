(ns simple-search.core
  (:use simple-search.knapsack-examples.knapPI_11_20_1000
        simple-search.knapsack-examples.knapPI_13_20_1000
        simple-search.knapsack-examples.knapPI_16_20_1000))

;;; An answer will be a map with (at least) four entries:
;;;   * :instance
;;;   * :choices - a vector of 0's and 1's indicating whether
;;;        the corresponding item should be included
;;;   * :total-weight - the weight of the chosen items
;;;   * :total-value - the value of the chosen items

(defn included-items
  "Takes a sequences of items and a sequence of choices and
  returns the subsequence of items corresponding to the 1's
  in the choices sequence."
  [items choices]
  (map first
       (filter #(= 1 (second %))
               (map vector items choices))))

(defn random-answer
  "Construct a random answer for the given instance of the
  knapsack problem."
  [instance]
  (let [choices (repeatedly (count (:items instance))
                            #(rand-int 2))
        included (included-items (:items instance) choices)]
    {:instance instance
     :choices choices
     :total-weight (reduce + (map :weight included))
     :total-value (reduce + (map :value included))}))

;;; It might be cool to write a function that
;;; generates weighted proportions of 0's and 1's.

(defn score
  "Returns the total value if the total-weight of the given answer is within capacity,
   otherwise returns the negative of the weight."
  [answer]
  (if (> (:total-weight answer)
         (:capacity (:instance answer)))
    (* (:total-weight answer) -1)
    (:total-value answer)))

(defn add-score
  "Computes the score of an answer and inserts a new :score field
   to the given answer, returning the augmented answer."
  [answer]
  (assoc answer :score (score answer)))

(defn random-search
  [instance max-tries]
  (apply max-key :score
         (map add-score
              (repeatedly max-tries #(random-answer instance)))))

(defn toggle-1-item
  "changes a value in :choices either from 0 to 1 or 1 to 0 to add or remove an item, then updates weight, value, and score"
  [answer index]
  (let [add-factor (if (= 0 (nth (:choices answer) index)) 1 -1)
        new-list (if (= -1 add-factor)
                   (apply list (assoc (into [] (:choices answer)) index 1))
                   (apply list (assoc (into [] (:choices answer)) index 0)))
        changed-item (nth (:items (:instance answer)) index)
        new-total-weight (+ (:total-weight answer) (* add-factor (:weight changed-item)))
        new-total-value (+ (:total-value answer) (* add-factor (:value changed-item)))
        new-answer-without-score (assoc answer :choices new-list
                                               :total-weight new-total-weight
                                               :total-value new-total-value)]
    (add-score new-answer-without-score)))

(defn hill-climb
  [answer tweak-fn number-of-iterations]
  (loop [current-best answer
         loops number-of-iterations]
    (if (zero? loops)
      current-best
      (let [tweaked-answer (tweak-fn current-best (rand-int (count (:choices current-best))))
            better-answer (max-key :score current-best tweaked-answer)]
      (recur better-answer (dec loops))))))

; Score is -2436
(def test-answer {:instance {:capacity 994, :items '({:value 403N, :weight 94N} {:value 886N, :weight 506N} {:value 814N, :weight 416N} {:value 1151N, :weight 992N} {:value 983N, :weight 649N} {:value 629N, :weight 237N} {:value 848N, :weight 457N} {:value 1074N, :weight 815N} {:value 839N, :weight 446N} {:value 819N, :weight 422N} {:value 1062N, :weight 791N} {:value 762N, :weight 359N} {:value 994N, :weight 667N} {:value 950N, :weight 598N} {:value 111N, :weight 7N} {:value 914N, :weight 544N} {:value 737N, :weight 334N} {:value 1049N, :weight 766N} {:value 1152N, :weight 994N} {:value 1110N, :weight 893N})}, :choices '(0 1 0 0 0 1 0 0 0 0 0 0 1 1 0 0 1 0 0 0), :total-weight 2436N, :total-value 4599N, :score -2436N}
  )

(hill-climb (add-score (random-answer knapPI_16_20_1000_1)) toggle-1-item 100000
            )

(time (random-search knapPI_16_20_1000_1 100000
))

(toggle-1-item {:instance {:capacity 994, :items '({:value 403N, :weight 94N} {:value 886N, :weight 506N} {:value 814N, :weight 416N} {:value 1151N, :weight 992N} {:value 983N, :weight 649N} {:value 629N, :weight 237N} {:value 848N, :weight 457N} {:value 1074N, :weight 815N} {:value 839N, :weight 446N} {:value 819N, :weight 422N} {:value 1062N, :weight 791N} {:value 762N, :weight 359N} {:value 994N, :weight 667N} {:value 950N, :weight 598N} {:value 111N, :weight 7N} {:value 914N, :weight 544N} {:value 737N, :weight 334N} {:value 1049N, :weight 766N} {:value 1152N, :weight 994N} {:value 1110N, :weight 893N})}, :choices '(0 1 0 0 0 1 0 0 0 0 0 0 1 1 0 0 1 0 0 0), :total-weight 2436N, :total-value 4599N, :score -2436N} 0
            )

(apply list (assoc (into [] '(1 2 3)) 0 0))

(assoc {:a 1 :b 2} :a 5 :b 7)

(count '(3 2 56 3))

(max-key :score {:thing 5 :score 7} {:thing 10 :score 3})
