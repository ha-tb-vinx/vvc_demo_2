REM ============================================================================
REM URIAGE-IF-BKUP
REM ============================================================================
call @filter.env.baseDirectory.value@\batch\z_common\SetBatchDAY.bat
set hh=%TIME:~0,2%
set hh=%hh: =0%
set mi=%TIME:~3,2%
set ss=%TIME:~6,2%
set now=%YY%%MM%%DD%%hh%%mi%%ss%

REM COPY-TO-DIR
set COPY_TO_TRAN_HOME=@filter.uriage.send.path@\URIAGE\

REM COPY-FROM-DIR
set IF_FILE_HOME=@filter.env.baseDirectory.value@\datas\ftpdata\LocalUser\noauth\uriage\
set IF_FILE_DISTRIBUTE=%IF_FILE_HOME%distribute\

REM ********************************
REM  FILE-BKUP
REM ********************************
copy %IF_FILE_DISTRIBUTE%backup\SMPU.ftp.zip %COPY_TO_TRAN_HOME%SMPU.ftp.%now%.zip
copy %IF_FILE_DISTRIBUTE%backup\SMPU_SABUN.ftp.zip %COPY_TO_TRAN_HOME%SMPU_SABUN.ftp.%now%.zip
copy %IF_FILE_DISTRIBUTE%IF_DT_KYAKUSU_JISEKI.csv %COPY_TO_TRAN_HOME%IF_DT_KYAKUSU_JISEKI.%now%.csv
copy %IF_FILE_DISTRIBUTE%IF_DT_DPT_URI_RUI.csv %COPY_TO_TRAN_HOME%IF_DT_DPT_URI_RUI.%now%.csv

:end 

:skip 

exit 0

:error

exit 1
