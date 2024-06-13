library verilog;
use verilog.vl_types.all;
entity SHIFT_OPERATOR is
    port(
        overflow        : out    vl_logic;
        \in\            : in     vl_logic_vector(3 downto 0);
        sleft           : in     vl_logic;
        sright          : in     vl_logic;
        \out\           : out    vl_logic_vector(3 downto 0)
    );
end SHIFT_OPERATOR;
