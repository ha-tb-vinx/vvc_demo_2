REM @echo off
REM ============================================================================
REM URIB840010_IfFastSalesSummaryCreate.bat
REM ver1.00 2020/09/25 G.SASAKI CREATE
REM ============================================================================
SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB840010
IF %ERRORLEVEL% NEQ 0 goto error

:end
CD %BATCHF_HOME%
exit 0

:error
exit 1
