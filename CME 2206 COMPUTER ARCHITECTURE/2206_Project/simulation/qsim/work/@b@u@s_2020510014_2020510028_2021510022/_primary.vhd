library verilog;
use verilog.vl_types.all;
entity BUS_2020510014_2020510028_2021510022 is
    port(
        ALU_overflow    : out    vl_logic;
        SM_Empty        : out    vl_logic;
        SM_Full         : out    vl_logic;
        CLK_Register    : in     vl_logic;
        CLK_Memory      : in     vl_logic;
        PC_Out          : out    vl_logic_vector(4 downto 0);
        SM_Out          : out    vl_logic_vector(4 downto 0);
        ALU_MUX_1_OUT   : out    vl_logic_vector(3 downto 0);
        GP_R_out_0      : out    vl_logic_vector(3 downto 0);
        GP_R_out_1      : out    vl_logic_vector(3 downto 0);
        GP_R_out_2      : out    vl_logic_vector(3 downto 0);
        \Input_R_\      : in     vl_logic_vector(3 downto 0);
        ALU_out         : out    vl_logic_vector(3 downto 0);
        DataM_Out       : out    vl_logic_vector(3 downto 0);
        ALU_MUX_2_OUT   : out    vl_logic_vector(3 downto 0);
        \Q_\            : out    vl_logic;
        AR_Out          : out    vl_logic_vector(3 downto 0);
        BUS_SEL         : out    vl_logic_vector(2 downto 0);
        GP_LD_EN        : out    vl_logic_vector(1 downto 0);
        opcode          : out    vl_logic_vector(3 downto 0);
        Output_R        : out    vl_logic_vector(3 downto 0);
        Rd_Out          : out    vl_logic_vector(1 downto 0);
        S1_Out          : out    vl_logic_vector(1 downto 0);
        S2_Out          : out    vl_logic_vector(1 downto 0);
        SP_Depth        : out    vl_logic_vector(3 downto 0);
        \T_\            : out    vl_logic_vector(2 downto 0)
    );
end BUS_2020510014_2020510028_2021510022;
