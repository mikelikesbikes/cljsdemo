(ns cljsdemo.utils
  (:use [jayq.core :only [$]]))

(defn on-load [f]
  (.load ($ js/window) f))

(defn on-user-input [f scope]
  (-> ($ :#chatbox)
    (.on (str "submit." scope)
      (fn [e] (.preventDefault e)
              (f (value :#message))))))

(defn on-user-register [f scope]
  (-> ($ :#registration)
    (.on (str "submit." scope)
      (fn [e] (.preventDefault e)
              (.hide ($ :#registration))
              (f (value :#handle) (value :#email))))))

(defn clear-user-input [f]
  (-> ($ :#message)
    (.val "")))

(defn html-escape [input]
  (reduce (fn [result [match replacement]] (string/replace result match replacement))
          input [["&" "&amp;"]
                 ["\"" "&quot;"]
                 ["'" "&#39;"]
                 ["<" "&lt;"]
                 [">" "&gt;"]]))

(defn append-msg-to [msg id]
  (let [el ($ id)
        obj (.get el 0)]
    (-> el
      (.append msg))
    (set! (.-scrollTop obj) (.-scrollHeight obj))))

(defn value [id]
  (.val ($ id)))