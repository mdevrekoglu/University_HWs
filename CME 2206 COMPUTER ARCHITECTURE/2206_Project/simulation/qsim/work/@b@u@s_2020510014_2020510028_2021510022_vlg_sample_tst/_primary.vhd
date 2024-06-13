library verilog;
use verilog.vl_types.all;
entity BUS_2020510014_2020510028_2021510022_vlg_sample_tst is
    port(
        CLK_Memory      : in     vl_logic;
        CLK_Register    : in     vl_logic;
        \Input_R_\      : in     vl_logic_vector(3 downto 0);
        sampler_tx      : out    vl_logic
    );
end BUS_2020510014_2020510028_2021510022_vlg_sample_tst;
