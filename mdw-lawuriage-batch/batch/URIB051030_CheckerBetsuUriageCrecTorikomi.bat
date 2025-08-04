REM @echo off
REM ============================================================================
REM タイトル	：チェッカー別売上Crec取込処理
REM 説明	：FTP集信した「チェッカー売上Crec精算」ファイルを「チェッカー精算売上ワーク」へ挿入する。
REM version 1.00 2016/11/14 J.Endo:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== チェッカー別売上取込処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB051030
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
