onerror {quit -f}
vlib work
vlog -work work CME2206Project_2020510014_2020510028_2021510022.vo
vlog -work work CME2206Project_2020510014_2020510028_2021510022.vt
vsim -novopt -c -t 1ps -L cycloneiii_ver -L altera_ver -L altera_mf_ver -L 220model_ver -L sgate work.BUS_2020510014_2020510028_2021510022_vlg_vec_tst
vcd file -direction CME2206Project_2020510014_2020510028_2021510022.msim.vcd
vcd add -internal BUS_2020510014_2020510028_2021510022_vlg_vec_tst/*
vcd add -internal BUS_2020510014_2020510028_2021510022_vlg_vec_tst/i1/*
add wave /*
run -all
