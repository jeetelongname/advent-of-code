(ns dev.user
  (:require [nextjournal.clerk :as clerk]))

(comment
  (clerk/serve! {:browse? true
                 :watch-paths ["notebooks"]})
  (clerk/show! "notebooks/day2.clj")
  (clerk/build! {:paths ["notebooks/*"]}))
