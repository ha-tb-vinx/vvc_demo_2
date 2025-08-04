REM @echo off
REM ============================================================================
REM タイトル	：DPT別売上累積データ集計処理
REM 説明	：「DPT別売上累積ワーク」と「関連会社売上累積ワーク」を集計し、
REM 		  「DPT別売上累積データ」ファイルを作成し営業情報へ渡す。
REM version 1.00 2009/07/07 Y.Taniwaki:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== DPT別売上累積データ集計処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB131030
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
