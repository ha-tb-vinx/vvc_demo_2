REM @echo off
REM ============================================================================
REM URIB860_IfTrePaymentInfoCsvCreateDao
REM ver1.00 2021/11/20 G.SASAKI CREATE 
REM ============================================================================
SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB860
IF %ERRORLEVEL% NEQ 0 goto error

CD %BATCHF_HOME%
exit 0

:error
exit 1
