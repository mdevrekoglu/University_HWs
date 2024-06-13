library verilog;
use verilog.vl_types.all;
entity BUS_2020510014_2020510028_2021510022_vlg_check_tst is
    port(
        ALU_MUX_1_OUT   : in     vl_logic_vector(3 downto 0);
        ALU_MUX_2_OUT   : in     vl_logic_vector(3 downto 0);
        ALU_out         : in     vl_logic_vector(3 downto 0);
        ALU_overflow    : in     vl_logic;
        AR_Out          : in     vl_logic_vector(3 downto 0);
        BUS_SEL         : in     vl_logic_vector(2 downto 0);
        DataM_Out       : in     vl_logic_vector(3 downto 0);
        GP_LD_EN        : in     vl_logic_vector(1 downto 0);
        GP_R_out_0      : in     vl_logic_vector(3 downto 0);
        GP_R_out_1      : in     vl_logic_vector(3 downto 0);
        GP_R_out_2      : in     vl_logic_vector(3 downto 0);
        opcode          : in     vl_logic_vector(3 downto 0);
        Output_R        : in     vl_logic_vector(3 downto 0);
        PC_Out          : in     vl_logic_vector(4 downto 0);
        \Q_\            : in     vl_logic;
        Rd_Out          : in     vl_logic_vector(1 downto 0);
        S1_Out          : in     vl_logic_vector(1 downto 0);
        S2_Out          : in     vl_logic_vector(1 downto 0);
        SM_Empty        : in     vl_logic;
        SM_Full         : in     vl_logic;
        SM_Out          : in     vl_logic_vector(4 downto 0);
        SP_Depth        : in     vl_logic_vector(3 downto 0);
        \T_\            : in     vl_logic_vector(2 downto 0);
        sampler_rx      : in     vl_logic
    );
end BUS_2020510014_2020510028_2021510022_vlg_check_tst;
