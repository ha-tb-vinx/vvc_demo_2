REM @echo off
REM ============================================================================
REM タイトル：日別店別DPT別精算データ作成処理(外部IF)
REM 説明	：「DPT別売上累積ワーク」と「関連会社売上累積ワーク」をUNIONし、
REM 		  「日別店別DPT別精算データ」ファイルを作成し勤怠システムへ渡す。
REM version 1.00 2009/09/28 S.Hamaguchi:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 日別店別DPT別精算データ作成処理(外部IF)
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB131040
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
