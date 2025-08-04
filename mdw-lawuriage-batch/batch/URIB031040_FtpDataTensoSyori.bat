REM @echo off
REM ============================================================================
REM タイトル	：FTP転送処理
REM 説明	：（商品実績）客数実績データをFTP転送する
REM version 3.00 2013/10/21 T.Ooshiro:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== FTP転送処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB031040
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
