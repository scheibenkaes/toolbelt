(ns toolbelt.core)

(defn flip
  "Similar to Haskells flip function it returns a function
  which when called flips its two arguments.
  (f %1 %2) -> (f %2 %1)"
  [f] #(f %2 %1))
