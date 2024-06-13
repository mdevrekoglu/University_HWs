library verilog;
use verilog.vl_types.all;
entity ALU_2020510014_2020510028_2021510022_vlg_sample_tst is
    port(
        opcode          : in     vl_logic_vector(2 downto 0);
        R1              : in     vl_logic_vector(3 downto 0);
        R2              : in     vl_logic_vector(3 downto 0);
        sampler_tx      : out    vl_logic
    );
end ALU_2020510014_2020510028_2021510022_vlg_sample_tst;
