(ns dev.user
  (:require [nextjournal.clerk :as clerk]))

(comment
  (clerk/serve! {:browse? true
                 :watch-paths ["notebooks"]}))
