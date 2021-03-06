(ns aoc2018-2
  (:require [clojure.string :as string]))

;; 파트 1
;; 주어진 각각의 문자열에서, 같은 문자가 두번 혹은 세번씩 나타난다면 각각을 한번씩 센다.
;; 두번 나타난 문자가 있는 문자열의 수 * 세번 나타난 문자가 있는 문자열의 수를 반환하시오.
;; 예)
;; abcdef 어떤 문자도 두번 혹은 세번 나타나지 않음 -> (두번 나오는 문자열 수: 0, 세번 나오는 문자열 수: 0)
;; bababc 2개의 a, 3개의 b -> (두번 나오는 문자열 수: 1, 세번 나오는 문자열 수: 1)
;; abbcde 2개의 b -> (두번 나오는 문자열 수: 2, 세번 나오는 문자열 수: 1)
;; abcccd 3개의 c -> (두번 나오는 문자열 수: 2, 세번 나오는 문자열 수: 2)
;; aabcdd 2개의 a, 2개의 d 이지만, 한 문자열에서 같은 갯수는 한번만 카운트함 -> (두번 나오는 문자열 수: 3, 세번 나오는 문자열 수: 2)
;; abcdee 2개의 e -> (두번 나오는 문자열 수: 4, 세번 나오는 문자열 수: 2)
;; ababab 3개의 a, 3개의 b 지만 한 문자열에서 같은 갯수는 한번만 카운트함 -> (두번 나오는 문자열 수: 4, 세번 나오는 문자열 수: 3)
;; 답 : 4 * 3 = 12

(defn get-sample-data [path]
  (->> (slurp path)
       (clojure.string/split-lines)))


(defn has-duplicated-word?
  "해당 횟수만큼 중복되는 텍스트가 있는지 확인하는 함수
  case: 1
    input: [2 abb]
    result: true
  case: 2
    input: [2 abc]
    result: false
   "
  [n word]
  (->> (frequencies word)
       vals
       (filter #(= % n))
       first))

(def has-two-duplicated? (partial has-duplicated-word? 2))

(def has-three-duplicated? (partial has-duplicated-word? 3))

(comment
  "day2 part1"
  (->> (get-sample-data "./resources/aoc2018_2.txt")
       (#(* (count (filter has-two-duplicated? %))
            (count (filter has-three-duplicated? %))))))

(comment
  (->> (slurp "./resources/aoc2018_2.txt")
       (clojure.string/split-lines)))


;; 파트 2
;; 여러개의 문자열 중, 같은 위치에 정확히 하나의 문자가 다른 문자열 쌍에서 같은 부분만을 리턴하시오.
;; 예)
;; abcde
;; fghij
;; klmno
;; pqrst
;; fguij
;; axcye
;; wvxyz

;; 주어진 예시에서 fguij와 fghij는 같은 위치 (2번째 인덱스)에 정확히 한 문자 (u와 h)가 다름. 따라서 같은 부분인 fgij를 리턴하면 됨.


;; #################################
;; ###        Refactoring        ###
;; #################################

(defn replace-to-dot
  "index에 해당하는 str를 '.' 으로 생성 
  input: [abcd 2]
  result: a.cd
 "
  [s n]
  (str (subs s 0 n) "." (subs s (+ n 1) (count s))))


(defn add-dot-to-all-case
  "문자열의 모든 index를 '.'으로 생성
  input: 'abcd'
  result: [.bcd a.cd ab.d abc.]
 "
  [s]
  (->> (range (count s))
       (map (partial replace-to-dot s))))

(comment
  "day2 part2"
  (->> (get-sample-data "./resources/aoc2018_2.txt")
       (map add-dot-to-all-case)
       flatten
       frequencies
       (filter #(= (second %) 2))
       ffirst
       (#(clojure.string/replace-first % "." ""))))

(comment
  (clojure.string/replace-first "cvgywxqubnuaefmsl.jdrpfzyi" "." ""))

(comment
  (->> (range (count "abcdef"))
       (map (partial replace-to-dot "abcdef"))
       frequencies))

(comment
  (->> (range (count "cvgowxquwnhaefmulkjdrptbyi"))
       (println #(subs "cvgowxquwnhaefmulkjdrptbyi" % 1))))