REM @echo off
REM ============================================================================
REM タイトル	：見切り実績データ取込処理
REM 説明	：メモリDB上で計算された「見切り実績データ」ファイルを元に、「見切り実績ワーク」を更新する。
REM version 1.00 2009/07/07 Y.Taniwaki:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 見切り実績データ取込処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB071030
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1

