REM @echo off
REM ============================================================================
REM タイトル	：アラーム出力処理
REM 説明	：POS実績取込処理(精算)でエラーデータが検出された場合、アラーム出力処理を実行する
REM version 3.00 2013/10/18 T.Ooshiro:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== アラーム出力処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012310
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
