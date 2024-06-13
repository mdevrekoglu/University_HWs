library verilog;
use verilog.vl_types.all;
entity ALU_2020510014_2020510028_2021510022 is
    port(
        overflow        : out    vl_logic;
        opcode          : in     vl_logic_vector(2 downto 0);
        R1              : in     vl_logic_vector(3 downto 0);
        R2              : in     vl_logic_vector(3 downto 0);
        Rout            : out    vl_logic_vector(3 downto 0)
    );
end ALU_2020510014_2020510028_2021510022;
