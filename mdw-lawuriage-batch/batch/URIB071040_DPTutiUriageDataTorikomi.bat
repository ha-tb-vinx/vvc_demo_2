REM @echo off
REM ============================================================================
REM タイトル	：DPT打ち売上データ取込処理
REM 説明	：メモリDB上で計算された「DPT打ち売上データ」ファイルを元に、「店別DPT打ち売上ワーク」を更新する。
REM version 1.00 2009/07/07 Y.Taniwaki:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== DPT打ち売上データ取込処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB071040
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1

