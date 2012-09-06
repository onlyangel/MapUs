package MapUs

import(
	"time"
)

type Track struct {
    IdVal int64
    Creation time.Time
}

type Position struct {
    IdVal int64
    Creation time.Time
    Acuracy float32
    Altitude float64
    Latitude float64
    Longitude float64
    Speed float32
    Time int64
    Bearing float32

    Track int64
}