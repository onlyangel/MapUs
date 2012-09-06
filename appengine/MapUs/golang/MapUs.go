package MapUs

import (
	"net/http"
    "fmt"
    "appengine"
    "appengine/datastore"
    "time"
    "encoding/json"
)

func init() {
    //http.HandleFunc("/", home)
    http.HandleFunc("/ws/createSecionId", createSecionId)
    http.HandleFunc("/ws/pushPos", pushPos)
    //http.HandleFunc("/ws/addList", listAdd)
    
}

func createSecionId(w http.ResponseWriter, r *http.Request) {
    c := appengine.NewContext(r)
	k := datastore.NewIncompleteKey(c, "Track", nil)

	ent := new(Track)
	ent.Creation = time.Now()

	key, err := datastore.Put(c, k, ent);
	if  err != nil {
       	http.Error(w, err.Error(), http.StatusInternalServerError)
        return 
    }
	ent.IdVal = key.IntID()
	datastore.Put(c,key,ent)

	fmt.Fprintf(w, "%d", ent.IdVal)
    return 

}

func pushPos(w http.ResponseWriter, r *http.Request) {
    
	data := r.FormValue("data")

	//fmt.Fprintf(w, "%#v",data)

	ent := new(Position)
	json.Unmarshal([]byte(data), &ent)

	ent.Creation = time.Now()

	//fmt.Fprintf(w, "%#v",ent)

	c := appengine.NewContext(r)
	k := datastore.NewIncompleteKey(c, "Position", nil)

	ent.Creation = time.Now()

	key, err := datastore.Put(c, k, ent);
	if  err != nil {
       	http.Error(w, err.Error(), http.StatusInternalServerError)
        return 
    }
	ent.IdVal = key.IntID()
	datastore.Put(c,key,ent)

	result := getPosiciones(r ,ent.Track)

	salida,_ := json.Marshal(result)

    fmt.Fprintf(w, "%s", salida)

    return 

}

func getPosiciones(r *http.Request, id int64) []Position{
	c := appengine.NewContext(r)
	q := datastore.NewQuery("Position").Filter("Track = ",id).Order("Creation")

	result := make([]Position,0,0)
	for t := q.Run(c); ; {
        var x Position
        _, err := t.Next(&x)
        if err == datastore.Done {
                break
        }
        if err != nil {
                return nil
        }
        result = append(result, x)
        
    }

    return result
}