REM @echo off
REM ============================================================================
REM title	：VATINVOICE cumulative processing
REM explanation	：To run the VATINVOICE cumulative processing.
REM version 1.00 2016/03/03 M.Kanno:Create New
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== VATINVOICE cumulative processing
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012130
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
