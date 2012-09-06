package MapUs

import (
	"net/http"
    "html/template"
    "os"
    "bufio"
    "fmt"
    "strconv"
)

func init() {
    //http.HandleFunc("/", home)
    http.HandleFunc("/ws/createSecionId", createSecionId)
    http.HandleFunc("/ws/addList", listAdd)
    
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