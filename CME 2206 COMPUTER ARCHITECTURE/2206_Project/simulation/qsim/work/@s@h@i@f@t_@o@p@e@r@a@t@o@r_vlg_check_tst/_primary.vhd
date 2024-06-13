library verilog;
use verilog.vl_types.all;
entity SHIFT_OPERATOR_vlg_check_tst is
    port(
        \out\           : in     vl_logic_vector(3 downto 0);
        overflow        : in     vl_logic;
        sampler_rx      : in     vl_logic
    );
end SHIFT_OPERATOR_vlg_check_tst;
